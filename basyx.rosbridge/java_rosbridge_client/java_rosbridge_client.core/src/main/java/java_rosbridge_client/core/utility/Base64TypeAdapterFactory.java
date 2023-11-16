/*******************************************************************************
 * Copyright (C) 2023 the Eclipse BaSyx Authors
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 * SPDX-License-Identifier: MIT
 ******************************************************************************/

package java_rosbridge_client.core.utility;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;


public final class Base64TypeAdapterFactory implements TypeAdapterFactory {
	
	public Base64TypeAdapterFactory() {
	
	}
	
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
		//Type Check
		Class<T> rawType = (Class<T>) type.getRawType();
	    if (rawType != Integer[].class) {
	    	System.out.println("Wrong type, expected Integer[], got " + rawType.toString());
	    	return null;
	    }
		final TypeAdapter<String> stringTypeAdapter = gson.getAdapter(String.class);

		return (TypeAdapter<T>) new Base64TypeAdapter(stringTypeAdapter);
	}
	
	
	private static final class Base64TypeAdapter extends TypeAdapter<Integer[]> {

		private static final Decoder base64Decoder = Base64.getDecoder();
		private static final Encoder base64Encoder = Base64.getEncoder();

		private final TypeAdapter<String> stringTypeAdapter;
			
		private Base64TypeAdapter(final TypeAdapter<String> stringTypeAdapter) {
			this.stringTypeAdapter = stringTypeAdapter;
		}
		
		@Override
		public void write(JsonWriter out, Integer[] value) throws IOException {
			byte[] encoderIn = new byte[value.length];

			for (int i = 0; i < value.length; i++) {
				encoderIn[i] = value[i].byteValue();
			}
			
			String encoding = base64Encoder.encodeToString(encoderIn);
			stringTypeAdapter.write(out, encoding);
		}

		@Override
		public Integer[] read(JsonReader in) throws IOException {
			final byte[] payload = base64Decoder.decode(stringTypeAdapter.read(in));
			
			//conversion from decoded signed byte values to original "unsigned" values
			Integer[] result = new Integer[payload.length];
			for (int i = 0; i < payload.length; i++) {
				result[i] = ((int) payload[i]) & 0xff;
			}

			return result;
		}
		
	}
}
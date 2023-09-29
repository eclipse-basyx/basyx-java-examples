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
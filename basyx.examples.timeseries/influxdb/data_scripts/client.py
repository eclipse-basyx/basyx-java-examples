from influxdb_client import InfluxDBClient, Point
from influxdb_client.client.write_api import SYNCHRONOUS
import json

bucket = "airsensors"

client = InfluxDBClient(url="http://influxdb:8086", token="d28e8bebb73e59c0cd88e8a1ac7855aa", org="basyx")

write_api = client.write_api(write_options=SYNCHRONOUS)
query_api = client.query_api()

f = open('air-sensor-data.lp')
f.readlines
for measurement in f.readlines():
    write_api.write(bucket=bucket, org='basyx', record=measurement)
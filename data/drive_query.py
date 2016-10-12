import predictionio

engine_client = predictionio.EngineClient(url="http://localhost:8000")
print engine_client.send_query({"temperature": 324, "frequency": 25, "voltage":151})
print engine_client.send_query({"temperature": 331, "frequency": 31, "voltage":266})
print engine_client.send_query({"temperature": 334, "frequency": 32, "voltage":286})

	
		
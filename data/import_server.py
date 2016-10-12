"""
Import sample data for lead scoring engine
"""
import argparse
import predictionio


def import_events(client):
  f = open("data.csv", 'r')
  count = 0
  for line in f:
	 data = line.rstrip('\r\n').split(",")
	 plan = data[3]
	 client.create_event(
        event="$set",entity_type="sensor",
        entity_id=str(count), 
        properties= {"temperature" : float(data[0]),"frequency" : float(data[1]),"voltage" : float(data[2]),"status" : int(plan)})
	 count=count+1
  return count


if __name__ == '__main__':
  parser = argparse.ArgumentParser(
    description="Import sample data for iot predictive maintenance")
  parser.add_argument('--access_key', default='invald_access_key')
  parser.add_argument('--url', default="http://localhost:7070")

  args = parser.parse_args()
  print args
  client = predictionio.EventClient(
    access_key=args.access_key,
    url=args.url,
    threads=4,
    qsize=100)
  count=import_events(client)
  print("Total records imported: " +str(count) )
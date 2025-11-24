import logging

from apache_beam import Pipeline,  Map, Create
from apache_beam.io.kafka import ReadFromKafka, WriteToKafka


def decode_kv(kv):
    key, value = kv
    return (key.decode("utf-8") if key else None,
            value.decode("utf-8") if value else None)

class KafkaReader:
    def build(self, p: Pipeline) -> None:
        emit_secs = 10
        # https://beam.apache.org/documentation/programming-guide/#1322-using-cross-language-transforms-in-a-python-pipeline
        (p | 'ReadFromKafka' >> ReadFromKafka(
            consumer_config = {
                'bootstrap.servers': 'localhost:9092',
                'group.id': 'beam-test',
            },
            topics=['test-topic'],)
         | "Decode" >> Map(decode_kv)
         | "Print" >> Map(print))

class KafkaWriter:
    def build(self, p: Pipeline) -> None:
        emit_secs = 10
        # https://beam.apache.org/documentation/programming-guide/#1322-using-cross-language-transforms-in-a-python-pipeline
        messages = p | Create(['Hello world'])

        (messages
         | Map(lambda x: (b'', x.encode())).with_output_types(tuple[bytes, bytes])
         |  'WriteToKafka' >> WriteToKafka(
                    producer_config = {
                        'bootstrap.servers': 'localhost:9092',
                        'group.id': 'beam-test',
                    },
                    topic='beam-topic',))


import logging
from typing import Tuple

from apache_beam import Pipeline,  Map
from apache_beam.io.kafka import ReadFromKafka, WriteToKafka
from apache_beam.pvalue import PCollection

from apps.examples.examples import Examples
from apps.helpers import print_msg
from apps.schemas import KafkaRecord, KafkaConsumerConfig, KafkaProducerConfig

KAFKA_TOPIC_IN = 'beam-topic-in'
KAFKA_TOPIC_OUT = 'beam-topic-out'
KAFKA_PRODUCER_GROUP_ID = 'beam-producer'
KAFKA_CONSUMER_GROUP_ID = 'beam-consumer'
BOOTSTRAP_HOST = 'localhost:9092'
KEY_DESERIALIZER = 'org.apache.kafka.common.serialization.ByteArrayDeserializer'
VALUE_DESERIALIZER = 'org.apache.kafka.common.serialization.ByteArrayDeserializer'
KEY_SERIALIZER = 'org.apache.kafka.common.serialization.ByteArraySerializer'
VALUE_SERIALIZER = 'org.apache.kafka.common.serialization.ByteArraySerializer'
EXPANSION_SERVICE = 'localhost:8097'

def decode_kv(kv):
    key, value = kv
    return (key.decode("utf-8") if key else None,
            value.decode("utf-8") if value else None)

def log_rec(elem: KafkaRecord):
    logging.info(elem.model_dump_json())

class KafkaReadWrite(Examples):
    """ """
    def build(self, p: Pipeline) -> None:
        """
        A simple pipline that beam will act as proxy to another topic

        :param p:
        :return: None
        """
        c_config = KafkaConsumerConfig(
            consumer_config = {
                'bootstrap.servers': BOOTSTRAP_HOST,
                'group.id': KAFKA_CONSUMER_GROUP_ID,
                'auto.offset.reset': "earliest"
            },
            topics=[KAFKA_TOPIC_IN],
            key_deserializer=KEY_DESERIALIZER,
            value_deserializer=VALUE_DESERIALIZER,
            with_metadata=False,
            expansion_service=EXPANSION_SERVICE
        )

        p_config = KafkaProducerConfig(
            producer_config={
                'bootstrap.servers': BOOTSTRAP_HOST,
                'group.id': KAFKA_PRODUCER_GROUP_ID,
            },
            topic=KAFKA_TOPIC_OUT,
            key_serializer=KEY_SERIALIZER,
            value_serializer=VALUE_SERIALIZER,
            expansion_service=EXPANSION_SERVICE
        )

        # https://beam.apache.org/documentation/programming-guide/#1322-using-cross-language-transforms-in-a-python-pipeline
        messages: PCollection = (p
            | 'ReadFromKafka' >> ReadFromKafka(**c_config.model_dump())
            | 'ToKafkaRecord' >> Map(KafkaRecord.fromKV)
        )

        (messages
         | Map(print_msg)
         | 'ToTuple' >> Map(lambda x: x.serialize()).with_output_types(Tuple[bytes,bytes])
         | 'WriteToKafka' >> WriteToKafka(**p_config.model_dump()))


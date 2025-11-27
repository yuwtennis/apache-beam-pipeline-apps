from typing import Tuple, Union
from pydantic import BaseModel


class GenericMessage(BaseModel):
    """ Simple message for general use case """
    message: str

class KafkaRecord(BaseModel):
    key: str
    val: str

    @staticmethod
    def fromKV(rec: Tuple[Union[None | bytes], Union[None | bytes]]) -> 'KafkaRecord':
        """
        This interface is used when header is not requested in consumer

        :param rec:
        :return:
        """
        return KafkaRecord(
            key=rec[0].decode('utf-8'),
            val=rec[1].decode('utf-8')
        )

    def serialize(self) -> Tuple[bytes, bytes]:
        """
        Serializes into format that kafka producer accepts
        :return:
        """
        return self.key.encode(), self.val.encode()

class KafkaConsumerConfig(BaseModel):
    consumer_config: dict[str, str]
    topics: list[str]
    key_deserializer: str
    value_deserializer: str
    with_metadata: bool
    expansion_service: str

class KafkaProducerConfig(BaseModel):
    producer_config: dict[str, str]
    topic: str
    key_serializer: str
    value_serializer: str
    expansion_service: str
import logging

from apache_beam import Pipeline
from apache_beam.options.pipeline_options import PipelineOptions

from apps.examples.kafka import KafkaReader


def main() -> None:
    logging.getLogger().setLevel(logging.INFO)
    options = PipelineOptions()
    with  Pipeline(options=options) as p:
        kafka = KafkaReader()
        kafka.build(p)

if __name__ == '__main__':
    main()
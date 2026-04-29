import apache_beam as beam
from apache_beam.testing.test_pipeline import TestPipeline
from apache_beam.testing.util import assert_that, equal_to


def test_beam_hello_world():
    with TestPipeline() as p:
        result = p  | beam.Create(["hello world"])

        assert_that(result, equal_to(["hello world"]))

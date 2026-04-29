
from apache_beam.testing.test_stream import TestStream
from apache_beam.testing.test_pipeline import TestPipeline
from apache_beam.testing.util import equal_to, assert_that


def test_streaming_element_equality():
    """ Simple test to check if streaming elements are equal """
    with (TestPipeline() as p):
        stream_events = TestStream().add_elements([1, 2, 3])
        result = p | stream_events
        assert_that(result, equal_to([1, 2, 3]))
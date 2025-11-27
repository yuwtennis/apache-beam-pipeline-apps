import importlib
import logging
import os

from apache_beam import Pipeline
from apache_beam.options.pipeline_options import PipelineOptions
from apache_beam.runners.interactive.display.pipeline_graph import PipelineGraph

from apps.helpers import parse_args


def main() -> None:
    logging.basicConfig(level=logging.INFO)
    _, pipeline_args = parse_args()

    example_class = getattr(
        importlib.import_module('apps.examples'),
        os.getenv('EXAMPLE_CLASS', 'HelloWorld')
    )

    with  Pipeline(options=PipelineOptions(pipeline_args)) as p:
        example = example_class()
        example.build(p)

#        graph = PipelineGraph(pipeline=p,).get_dot()
#
#        with open('/tmp/pipeline_graph.dot', 'w') as fd:
#            fd.write(graph)

if __name__ == '__main__':
    main()
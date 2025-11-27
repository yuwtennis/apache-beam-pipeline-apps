from apache_beam import Pipeline, Create
from apache_beam import Map

from apps.examples.examples import Examples
from apps.helpers import print_msg
from apps.schemas import GenericMessage


class HelloWorld(Examples):
    """ """
    def build(self, p: Pipeline):
        (p
         | Create([GenericMessage(message="Hello world")])
        | 'Print' >> Map(print_msg) )

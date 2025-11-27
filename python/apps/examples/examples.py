import abc
from apache_beam import Pipeline


class Examples(metaclass=abc.ABCMeta):

    @abc.abstractmethod
    def build(self, p: Pipeline) -> Pipeline:
        raise(NotImplementedError())

import argparse
import logging
from typing import Tuple

from pydantic import BaseModel

LOGGER = logging.getLogger(__name__)

def parse_args() -> Tuple[argparse.Namespace, list[str]]:
    """ """
    parser = argparse.ArgumentParser()

    return parser.parse_known_args()


def print_msg(elem: BaseModel) -> BaseModel:
    """ """
    LOGGER.info(elem.model_dump())

    return elem
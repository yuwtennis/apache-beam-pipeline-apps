""" Example of customizing DoFn to setup Database client """

import logging
import apache_beam as beam
from apache_beam.options.pipeline_options import PipelineOptions
from sqlalchemy import create_engine, select, ScalarResult, Result, text
from sqlalchemy.orm import DeclarativeBase, Mapped, Session, mapped_column


def connect():
    return create_engine("postgresql+psycopg://postgres:postgres@localhost:5432/sampledb")

class Base(DeclarativeBase):
    pass

class MyTable(Base):
    __tablename__ = "mytable"
    id: Mapped[int] = mapped_column(primary_key=True)
    name: Mapped[str]


class CustomizedDOFN(beam.DoFn):

    def setup(self):
        """ This is the initialization of DoFn lifecycle """
        logging.info("Initializing the DoFn. Setting up database connection.")
        self._session = Session(connect())
        self._cache = None

    def start_bundle(self):
        """ This is the beginning for processing a bundle """

        # NOTE WeakRef for select() in SQLAlchemy that causes serialization to fail. Instead, used raw sql
        self._cache = self._session.execute(text("SELECT * FROM mytable")).fetchall()
        pass

    def process(self, element):
        """ This is the per-element processing """
        logging.info(f"View of lookup table: {self._cache}")
        #do_something_with_the_list()
        pass

    def finish_bundle(self):
        """ This is the end of processing a bundle """
        #write(cache())

        pass

    def teardown(self):
        if self._session is not None:
            self._session.close()


def main():
    logging.basicConfig(level=logging.INFO)
    option = PipelineOptions(runner='DirectRunner')

    with (beam.Pipeline(options=option) as p):
        (p
         | beam.Create(["Hello", "World"])
         | beam.ParDo(CustomizedDOFN()))

if __name__ == "__main__":
    main()
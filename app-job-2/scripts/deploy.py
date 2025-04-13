import argparse
import os
import sys
import time
import json
import logging

from pathlib import Path
from typing import Any, Dict, List, Optional
from datetime import datetime


from app_job_2 import __version__ as app_version
from app_job_2 import __name__ as app_name
from app_job_2 import __description__ as app_description
from app_job_2 import __author__ as app_author
from app_job_2 import __email__ as app_email



# get parameters from command line
def get_parameters() -> argparse.Namespace:
    parser = argparse.ArgumentParser(description="Deploy script for app-job-2.")
    parser.add_argument(
        "--config",
        type=str,
        default="config.json",
        help="Path to the configuration file.",
    )
    parser.add_argument(
        "--output",
        type=str,
        default="output.json",
        help="Path to the output file.",
    )
    parser.add_argument(
        "--verbose",
        action="store_true",
        help="Enable verbose logging.",
    )
    return parser.parse_args()

# setup logging
def setup_logging(verbose: bool) -> None:
    log_level = logging.DEBUG if verbose else logging.INFO
    logging.basicConfig(
        level=log_level,
        format="%(asctime)s - %(levelname)s - %(message)s",
    )
    logging.info("Logging is set up.")

#print the command line arguments
def print_args(args: argparse.Namespace) -> None:
    logging.info("Command line arguments:")
    for arg, value in vars(args).items():
        logging.info(f"{arg}: {value}")
    logging.info("Command line arguments printed.")
    
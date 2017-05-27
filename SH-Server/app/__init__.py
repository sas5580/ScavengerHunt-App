from flask import Flask

__all__ = ["Game", "Objective", "Player"]

from .lib.Game import Game
from .lib.Objective import Objective
from .lib.Player import Player

app = Flask(__name__)
from app import views
from app import request

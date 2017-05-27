from flask import Flask

__all__ = ["Game", "Objective", "Player"]

from .library.Game import Game
from .library.Objective import Objective
from .library.Player import Player

app = Flask(__name__)
from app import views
from app import request

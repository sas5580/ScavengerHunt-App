import time
import uuid

from Player import Player
from Objective import Objective

class Game:
    __game_map = {}

    @staticmethod
    def get_list():
        return Game.__game_map.keys()

    @staticmethod
    def get_by_key(key):
        return Game.__game_map[key]

    def __init__(self, ownerid):
        self.timestamp = time.time()
        self.key = uuid.uuid1()
        self.ownderid = ownerid
        self.players = {}
        self.objectives = {}

        self.started = False
        self.start_time = None

        Game.__game_map[self.key] = self
        
    def serialize(self):
    	return {'started': started}

    def add_player(self, name):
        player = Player(name, self.key)
        self.players[player.id] = player

    def player_list(self):
        return self.players.values()

    def add_objective(self, latitude, longitude, description):
        objective = Objective((latitude, longitude), description, self.key)
        self.objectives[objective.location] = objective

    def objective_list(self):
        return self.objectives.values()

    def start(self):
        self.start_time = time.time()
        self.started = True 




import uuid

from Objective import Objective

class Player:
    __player_map = {}

    @staticmethod
    def get_all():
        return Player.__player_map.values()

    @staticmethod
    def get_by_id(id):
        return Player.__player_map[id]

    def __init__(self, name, game_key):
        self.id = uuid.uuid1()
        self.name = name
        self.game_key = game_key
        self.objectives_complete = []

        # Tuple: (lat, long)
        self.location = None

        Player.__player_map[self.id] = self

    def update_location(self, location):
        self.location = location

    def complete_objective(self, location):
        self.objectives_complete.append(location)
        Objective.get_by_location(location).player_complete(self.id)

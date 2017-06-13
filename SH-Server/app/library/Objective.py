class Objective:
    __objective_map = {}

    @staticmethod
    def get_by_location(location):
        return Objective.__objective_map[location]

    def __init__(self, location, description, gameKey):
        self.location = location
        self.description = description
        self.gameKey = gameKey
        self.players_completed = []

        Objective.__objective_map[self.location] = self

    def player_complete(self, id):
        self.players_completed.append(id)


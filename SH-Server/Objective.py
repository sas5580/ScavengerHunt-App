def Objective:

    def __init__(self, latitude, longitude, description, gameKey):
        self.location = (latitude, longitude)
        self.description = description
        self.gameKey = gameKey

        self.playersCompleted = []

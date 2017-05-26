class Player:

    def __init__(self, uid, name, gameKey):
        self.id = uid
        self.name = name
        self.gameKey = gameKey
        
        # Tuple: (lat, long)
        self.location = None
                

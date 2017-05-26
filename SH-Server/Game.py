import time
from Player import Player
from Objective import Objective

class Game:

    def __init__(self, uid, ownerId):
        self.timestamp = time.strftime("%Y-%m-%d %H:%M:%S", time.gmtime())
        self.key = uid
        self.ownderId = ownerId
        self.players = []
        self.objectives = []

        self.started = False
        self.startTime = None
        
        
        
    
        
        

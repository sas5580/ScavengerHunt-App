from flask import jsonify, make_response
from flask_socketio import emit
from app import app, socketio, Game, Objective, Player
import datetime
import json

games = {}

game = Game(100, 1000)

player1 = Player(10000, 'Yan', 100)
player2 = Player(10001, 'Sas', 100)

objective1 = Objective(1111,1112, "description" ,100)
objective2 = Objective(2222,2223, "description2", 100)

game.players = [player1, player2]
game.objectives = [objective1, objective2]

games[game.key] = game

@socketio.on('my_event')
def test_connect(message):
	print message['data']
	emit('penis', {'data': 'dur'})

@socketio.on('connect')
def connect():
	print "connect"

@socketio.on('disconnect')
def disconnect():
	print "disconnect"

@socketio.on('heart_beat')
def heartBeat(message):
	time = datetime.datetime.now()
	emit('heartbeat',{'data': time})

@socketio.on('get_game_info')
def getGameInfo(message):
	print 'get_game_info: ', message
	
	print message['game']

	print message['game'] is not None
	print message['game'] in games
	emit('game_info' , {'data' : 'get game info'})

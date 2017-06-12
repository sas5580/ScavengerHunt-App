from flask import jsonify, make_response
from flask_socketio import emit
from app import app, socketio, Game, Objective, Player


@socketio.on('test')
def test_connect(message):
    print "TEST"
    print message
    socketio.emit('my_response', {'data': 'TEST'})

@socketio.on('message')
def handle_message(message):
    print('received message: ' + message)

@socketio.on('json')
def handle_json(json):
    print('received json: ' + str(json))
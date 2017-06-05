from flask import jsonify, make_response
from flask_socketio import emit
from app import app, socketio, Game, Objective, Player

@socketio.on('test')
def test_connect():
    print "TEST"
    socketio.emit('my_response', {'data': 'TEST'})
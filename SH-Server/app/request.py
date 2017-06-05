from flask import jsonify, make_response
from flask_socketio import emit
from app import app, socketio, Game, Objective, Player

@socketio.on('my_event')
def test_connect(message):
    print "hur"
    emit('penis', {'data': 'dur'})
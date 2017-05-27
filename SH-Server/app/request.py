from flask import jsonify, make_response
from app import app, Game, Objective, Player

@app.route('/api/')

@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)
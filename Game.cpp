// bvandyke17@georgefox.edu
// assignment 7
// 2019-03-23

#include "King.h"
#include "Pawn.h"
#include "Player.h"
#include "Game.h"
#include "Board.h"
#include "Rook.h"
#include "Knight.h"
#include "Bishop.h"
#include "Queen.h"

#include <iostream>

using namespace std;

const int DIMENSION = 8;
const int BLACK_FIRST_ROW = 0;
const int WHITE_FIRST_ROW = 7;
const int BLACK_SECOND_ROW = 1;
const int WHITE_SECOND_ROW = 6;
const int L_ROOK = 0;
const int R_ROOK = 7;
const int L_KNIGHT = 1;
const int R_KNIGHT = 6;
const int L_BISHOP = 2;
const int R_BISHOP = 5;
const int KING = 4;
const int QUEEN = 3;
const int PAWN_VALUE = 1;
const int ROOK_VALUE = 2;
const int KNIGHT_VALUE = 3;
const int BISHOP_VALUE = 4;
const int QUEEN_VALUE = 5;
const int KING_VALUE = 6;

Board* Game::_board = nullptr;
list<Piece*> Game::_whitePieces;
list<Piece*> Game::_blackPieces;
Player* Game::_playerOne = nullptr;
Player* Game::_playerTwo = nullptr;
Player* Game::_nextPlayer = nullptr;

void Game::initialize()
{
    _board = Board::getInstance();

    _board->_squares[BLACK_FIRST_ROW][L_ROOK]->setOccupant(new Rook("B", _board->_squares[BLACK_FIRST_ROW][L_ROOK],
            ROOK_VALUE));
    _board->_squares[BLACK_FIRST_ROW][R_ROOK]->setOccupant(new Rook("B", _board->_squares[BLACK_FIRST_ROW][R_ROOK],
            ROOK_VALUE));

    _board->_squares[BLACK_FIRST_ROW][L_KNIGHT]->setOccupant(new Knight("B",
            _board->_squares[BLACK_FIRST_ROW][L_KNIGHT], KNIGHT_VALUE));
    _board->_squares[BLACK_FIRST_ROW][R_KNIGHT]->setOccupant(new Knight("B",
            _board->_squares[BLACK_FIRST_ROW][R_KNIGHT], KNIGHT_VALUE));

    _board->_squares[BLACK_FIRST_ROW][L_BISHOP]->setOccupant(new Bishop("B",
            _board->_squares[BLACK_FIRST_ROW][L_BISHOP], BISHOP_VALUE));
    _board->_squares[BLACK_FIRST_ROW][R_BISHOP]->setOccupant(new Bishop("B",
            _board->_squares[BLACK_FIRST_ROW][R_BISHOP], BISHOP_VALUE));

    _board->_squares[BLACK_FIRST_ROW][KING]->setOccupant(new King("B", _board->_squares[BLACK_FIRST_ROW][KING],
            KING_VALUE));

    _board->_squares[BLACK_FIRST_ROW][QUEEN]->setOccupant(new Queen("B", _board->_squares[BLACK_FIRST_ROW][QUEEN],
            QUEEN_VALUE));

    for (int i = 0; i < DIMENSION; i++)
    {
        _board->_squares[BLACK_SECOND_ROW][i]->setOccupant(new Pawn("B", _board->_squares[BLACK_SECOND_ROW][i],
                PAWN_VALUE));

        _board->_squares[WHITE_SECOND_ROW][i]->setOccupant(new Pawn("W", _board->_squares[WHITE_SECOND_ROW][i],
                PAWN_VALUE));
    }

    _board->_squares[WHITE_FIRST_ROW][L_ROOK]->setOccupant(new Rook("W", _board->_squares[WHITE_FIRST_ROW][L_ROOK],
            ROOK_VALUE));
    _board->_squares[WHITE_FIRST_ROW][R_ROOK]->setOccupant(new Rook("W", _board->_squares[WHITE_FIRST_ROW][R_ROOK],
            ROOK_VALUE));

    _board->_squares[WHITE_FIRST_ROW][L_KNIGHT]->setOccupant(new Knight("W",
            _board->_squares[WHITE_FIRST_ROW][L_KNIGHT], KNIGHT_VALUE));
    _board->_squares[WHITE_FIRST_ROW][R_KNIGHT]->setOccupant(new Knight("W",
            _board->_squares[WHITE_FIRST_ROW][R_KNIGHT], KNIGHT_VALUE));

    _board->_squares[WHITE_FIRST_ROW][L_BISHOP]->setOccupant(new Bishop("W",
            _board->_squares[WHITE_FIRST_ROW][L_BISHOP], BISHOP_VALUE));
    _board->_squares[WHITE_FIRST_ROW][R_BISHOP]->setOccupant(new Bishop("W",
            _board->_squares[WHITE_FIRST_ROW][R_BISHOP], BISHOP_VALUE));

    _board->_squares[WHITE_FIRST_ROW][KING]->setOccupant(new King("W", _board->_squares[WHITE_FIRST_ROW][KING],
            KING_VALUE));

    _board->_squares[WHITE_FIRST_ROW][QUEEN]->setOccupant(new Queen("W", _board->_squares[WHITE_FIRST_ROW][QUEEN],
            QUEEN_VALUE));

    for (int i = 0; i < DIMENSION; i++)
    {
        for (int j = 0; j < DIMENSION; j++)
        {
            if (_board->_squares[i][j]->isOccupied())
            {
                if (_board->_squares[i][j]->getOccupant()->getColor() == "W")
                {
                    _whitePieces.push_back(_board->_squares[i][j]->getOccupant());
                }

                else
                {
                    _blackPieces.push_back(_board->_squares[i][j]->getOccupant());
                }
            }
        }
    }

    _playerOne = new Player("White", _board->_squares[WHITE_FIRST_ROW][KING]->getOccupant(), _whitePieces);
    _playerTwo = new Player("Black", _board->_squares[BLACK_FIRST_ROW][KING]->getOccupant(), _blackPieces);

    for (int i = 0; i < DIMENSION; i++)
    {
        for (int j = 0; j < DIMENSION; j++)
        {
            if (_board->_squares[i][j]->isOccupied())
            {
                _board->_squares[i][j]->getOccupant()->setLocation(_board->_squares[i][j]);
            }
        }
    }
    _nextPlayer = _playerTwo;

    _board->display(cout);
}

void Game::nukeEverything()
{
    delete _playerOne;
    delete _playerTwo;

    Board::getInstance()->deleteInstance();
}

Player* Game::getNextPlayer()
{
    if (_nextPlayer == _playerTwo)
    {
        _nextPlayer = _playerOne;
    }

    else
    {
        _nextPlayer = _playerTwo;
    }

    return _nextPlayer;
}

Player* Game::getOpponentOf(Player* player)
{
    Player* returnedPlayer = _playerTwo;

    if (player == _playerTwo)
    {
        returnedPlayer = _playerOne;
    }

    return returnedPlayer;
}
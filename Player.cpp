// bvandyke17@georgefox.edu
// assignment 7
// 2019-03-23

#include "Player.h"
#include "Game.h"
#include "Board.h"
#include <stdexcept>
#include <iostream>

Player::Player(string playerName, Piece* playerKing, list<Piece*>& playerPieces): _name(playerName), _king(playerKing),
_pieces(playerPieces) {}

Player::~Player() {}

string Player::getName()
{
    return _name;
}

Piece* Player::getKing()
{
    return _king;
}

list<Piece*>& Player::getPieces()
{
    return _pieces;
}

bool Player::makeMove(string& fromSquare, string& toSquare)
{
    const int VALID_LENGTH = 2;
    const int VALID_FILE = 0;
    const int VALID_RANK = 1;
    const int DIMENSION = 8;
    const int UPPER_BOUND = 7;
    const int LOWER_BOUND = 0;
    const char ASCII_LOWER_CHAR = 'a';
    const char ASCII_LOWER_NUM = '0';

    int fromFile;
    int fromRank;
    int toFile;
    int toRank;
    bool madeMove;

    bool drawOption = false;
    Board* board = Board::getInstance();

    // validate legal argument length
    if (fromSquare.length() != VALID_LENGTH || toSquare.length() != VALID_LENGTH)
    {
        // validate draw option play
        if (toSquare.length() == 3 && toSquare.at(2) == '=')
        {
            drawOption = true;
        }

        else
        {
            throw invalid_argument("Invalid Square.");
        }
    }

    // convert user input and assign variables
    fromFile = fromSquare.at(VALID_FILE) - ASCII_LOWER_CHAR;
    fromRank = DIMENSION - (fromSquare.at(VALID_RANK) - ASCII_LOWER_NUM);
    toFile = toSquare.at(VALID_FILE) - ASCII_LOWER_CHAR;
    toRank = DIMENSION - (toSquare.at(VALID_RANK) - ASCII_LOWER_NUM);

    // validate nonsensical squares
    if (fromFile < LOWER_BOUND || fromRank < LOWER_BOUND || toFile < LOWER_BOUND || toRank < LOWER_BOUND ||
        fromFile > UPPER_BOUND || fromRank > UPPER_BOUND || toFile > UPPER_BOUND || toRank > UPPER_BOUND)
    {
        throw invalid_argument("Invalid square.");
    }

    // validate moving from an empty square, or illegal move of piece, or moving other player's pieces
    if (!board->getSquareAt(fromRank, fromFile)->isOccupied() || !board->getSquareAt(fromRank, fromFile)->
        getOccupant()->canMoveTo(board->getSquareAt(toRank, toFile)) || (getKing()->getColor() !=
        board->getSquareAt(fromRank, fromFile)->getOccupant()->getColor()))
    {
        throw invalid_argument("Invalid piece move.");
    }

    // validate illegal capture of own piece
    else if (board->getSquareAt(toRank, toFile)->isOccupied() && board->getSquareAt(toRank, toFile)->getOccupant()->
        getColor() == board->getSquareAt(fromRank, fromFile)->getOccupant()->getColor())
    {
        throw invalid_argument("Cannot capture own piece.");
    }

    else
    {
        // kill enemy piece if moving to hostile occupied square. Take no prisoners.
        if (board->getSquareAt(toRank, toFile)->isOccupied() && board->getSquareAt(toRank, toFile)->getOccupant()->
            getColor() != getKing()->getColor())
        {
            capture(board->getSquareAt(toRank, toFile)->getOccupant());
        }

        // advance soldier to establish a foothold on square. Move to square with violence of action.
        madeMove = Board::getInstance()->getSquareAt(fromRank, fromFile)->getOccupant()->moveTo(Board::getInstance()->
                getSquareAt(toRank, toFile), this);

        if (drawOption)
        {
            cout << getName() + " player offers a draw." << endl;
        }
    }

    return madeMove;
}

void Player::capture(Piece* piece)
{
    // cuff and bag prisoner
    Game::getOpponentOf(this)->getPieces().remove(piece);

    delete piece;
}




// bvandyke17@georgefox.edu
// assignment 7
// 2019-03-23

#include "Board.h"
#include <iostream>

Board* Board::_instance = new Board();

void Board::deleteInstance()
{
    for (int i = 0; i < DIMENSION; i++)
    {
        for (int j = 0; j < DIMENSION; j++)
        {
            if (_instance->getSquareAt(i, j)->isOccupied())
            {
                delete _instance->getSquareAt(i, j)->getOccupant();
            }
            delete _instance->getSquareAt(i, j);
        }
    }

    delete _instance;
}

Board* Board::getInstance()
{
    if (_instance == nullptr)
    {
        _instance = new Board();
    }

    return _instance;
}

Square* Board::getSquareAt(int rank, int file)
{
    return _squares[rank][file];
}

bool Board::isClearRank(Square* from, Square* to)
{
    const int SQUARE_BEFORE = 1;
    bool isClear = true;
    int rank = from->getRank();
    int file = from->getFile();

    if (from->getFile() != to->getFile())
    {
        isClear = false;
    }

    else
    {
        // exclude the to square and check in move to if the to square is the opponent
        // moving down
        if (rank > to->getRank())
        {
            while (rank > to->getRank() + SQUARE_BEFORE && isClear)
            {
                rank--;

                if (Board::getInstance()->getSquareAt(rank, file)->isOccupied())
                {
                    isClear = false;
                }
            }
        }

        else
        {
            while (rank < to->getRank() - SQUARE_BEFORE && isClear)
            {
                rank++;

                if (Board::getInstance()->getSquareAt(rank, file)->isOccupied())
                {
                    isClear = false;
                }
            }
        }
    }
    return isClear;
}

bool Board::isClearFile(Square* from, Square* to)
{
    const int SQUARE_BEFORE = 1;
    bool isClear = true;
    int rank = from->getRank();
    int file = from->getFile();


    if (from->getRank() != to->getRank())
    {
        isClear = false;
    }

    else
    {
        // exclude the to square and check in move to if the to square is the opponent
        // moving left
        if (file > to->getFile())
        {
            while (file > to->getFile() + SQUARE_BEFORE && isClear)
            {
                file--;

                if (Board::getInstance()->getSquareAt(rank, file)->isOccupied())
                {
                    isClear = false;
                }
            }
        }

        else
        {
            while (file < to->getFile() - SQUARE_BEFORE && isClear)
            {
                file++;

                if (Board::getInstance()->getSquareAt(rank, file)->isOccupied())
                {
                    isClear = false;
                }
            }
        }
    }
    return isClear;
}

bool Board::isClearDiagonal(Square* from, Square* to)
{
    const int SQUARE_BEFORE = 1;
    bool isClear = true;
    Board* board = Board::getInstance();
    int rank;
    int file;

    // check if moving to nonsensical location
    if (from->getRank() == to->getRank() || from->getFile() == to->getFile() ||
        abs(from->getRank() - to->getRank()) != abs(from->getFile() - to->getFile()) )
    {
        isClear = false;
    }

    rank = from->getRank();
    file = from->getFile();

    // moving piece down board
    if (from->getRank() < to->getRank())
    {
        while (rank < to->getRank() - SQUARE_BEFORE && isClear)
        {
            rank++;

            // check direction down right
            if (file < to->getFile())
            {
                file++;

                if (board->getSquareAt(rank, file)->isOccupied())
                {
                    isClear = false;
                }
            }

            // check direction down left
            else
            {
                file--;

                if (board->getSquareAt(rank, file)->isOccupied())
                {
                    isClear = false;
                }
            }
        }
    }

    // moving piece up board
    else
    {
        while (rank > to->getRank() + SQUARE_BEFORE && isClear)
        {
            rank--;

            // check direction up right
            if (file < to->getFile())
            {
                file++;

                if (board->getSquareAt(rank, file)->isOccupied())
                {
                    isClear = false;
                }
            }

            // check direction down left
            else
            {
                file--;

                if (board->getSquareAt(rank, file)->isOccupied())
                {
                    isClear = false;
                }
            }
        }
    }

    return isClear;
}

void Board::display(ostream& os)
{
    int displayCount = DIMENSION;
    os << "     a     b     c     d     e     f     g     h  " << endl;
    for (int i = 0; i < DIMENSION; i++)
    {
        os << "  +-----+-----+-----+-----+-----+-----+-----+-----+" << endl;
        os << displayCount << " | ";
        for (int j = 0; j < DIMENSION; j++)
        {

            if (getSquareAt(i, j)->isOccupied())
            {
                os << " ";
                getSquareAt(i, j)->getOccupant()->display(os);
            }

            else
            {
                os << "   ";
            }

            os << " | ";

        }

        os  << displayCount-- << endl;

    }

    os << "  +-----+-----+-----+-----+-----+-----+-----+-----+" << endl;
    os << "     a     b     c     d     e     f     g     h  " << endl;
}

Board::Board()
{
    for (int i = 0; i < DIMENSION; i++)
    {
        for (int j = 0; j < DIMENSION; j++)
        {
            _squares[i][j] = new Square(i, j);
            _squares[i][j]->setOccupant(nullptr);
        }
    }
}
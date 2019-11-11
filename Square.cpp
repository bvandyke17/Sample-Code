// bvandyke17@georgefox.edu
// assignment 7
// 2019-03-23

#include "Square.h"

Square::Square(int newRank, int newFile): _rank(newRank), _file(newFile) {}

Square::~Square() {}

int Square::getRank()
{
    return _rank;
}

int Square::getFile()
{
    return _file;
}

bool Square::isOccupied()
{
    return _occupant != nullptr;
}

Piece* Square::getOccupant()
{
    return _occupant;
}

void Square::setOccupant(Piece* newOccupant)
{
    _occupant = newOccupant;
}
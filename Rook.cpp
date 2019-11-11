// bvandyke17@georgefox.edu
// assignment 7
// 2019-03-23

#include "Rook.h"
#include "Board.h"

void Rook::display(ostream& os)
{
    os << getColor() + "R";
}

bool Rook::canMoveTo(Square* location)
{
    const int ZERO_SQUARES = 0;
    bool move = Board::getInstance()->isClearFile(getLocation(), location);

    if (abs(location->getRank() - getLocation()->getRank()) != ZERO_SQUARES)
    {
        move = Board::getInstance()->isClearRank(getLocation(), location);
    }


    return move;
}
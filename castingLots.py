# bvandyke17@georgefox.edu
# program 11
# 2017-12-06

# import random
from random import randint, choice
def main():
    """
    this is the main function
    """
    # create flag to start/stop loop
    quitFlag = False

    # get total amount of talents from opened file
    total = openFile()
    print('Welcome to the lots game. \nTalents you have to play with:', total)

    # create main loop with flag
    while not quitFlag:
        # get user input for menu options
        userMove = menu(total)

        # assign flag variable to start/stop based on menu input
        quitFlag = menuPlay(userMove)

        # create if statement for user input of 1
        if userMove == 1:

            # get user wager
            wager = userWager()
            # assign variable for dice roll 
            totalDice = diceRoll()

            # get total talents from the field bet play
            total = fieldBetPlay(totalDice, wager, total)

            # update total talents
            remainingTotal(total)

            # quit loop if no talents remain
            quitFlag = zeroTalentsRemaining(total)

            # create if statement for user input of 2
        if userMove == 2:

            # get user wager
            wager = userWager()

            # assign variable for dice roll
            totalDice = diceRoll()

            # get total talents from the pass bet play
            total = passBetPlay(totalDice, wager, total)

            # update total talents
            remainingTotal(total)

            # quit loop if no talents remain
            quitFlag = zeroTalentsRemaining(total)

        # create if statement for user input of 3
        if userMove == 3:

            # assign flag to quit loop
            quitFlag = True

    # save data onto file
    appendNewTotal(total)

def openFile():
    """
    this function opens the file and assigns talents value
    """
    # open file
    # get amount of talents in file
    try:
        gameFile = open('talents.txt', 'r')
        # assign variable
        talents = int(gameFile.readline())
        # assign variable for talents if none remain
        if talents <= 0:
            talents = 100
        # close file
        gameFile.close()
    # create exception
    except IOError:
        print('You have been given 100 talents.' 
              '\nMay the odds forever be in your favor.')
        # assign variable if no file is found
        talents = 100
    # return variable 
    return talents

def menu(total):
    """
    this function is the menu for the program
    """
    # display welcome and data
    print('\n1-Field', '2-Pass', '3-Quit', sep='\n')
    print('Choice' + ('-' * 27), end='')

    # create list of input options
    optionList = [1, 2, 3]

    # assign variable to start loop
    userChoice = 0

    # crate loop for validation of user input
    while userChoice not in optionList:
        try:
            # get user input
            userChoice = int(input(''))
            if userChoice not in optionList:
                print('Please enter a valid option:', end='')

        # validate input
        except ValueError:
            print('Please enter a number to play:', end='')

    # return user input
    return userChoice

def menuPlay(userInput):
    """
    this function ends the loop if user input is 3
    """
    if userInput == 3:
        # assign variable
        flag = True

    else:
        flag = False
    # return variable
    return flag

def userWager():
    """
    this function gets the user input for their wager
    """
    # assign variable to start loop
    wager = -1
    print('How much do you wish to wager?' + ('-' * 3), end='')

    # create loop for user input validation
    while wager < 0:
        try:
            # get user input
            wager = int(input(''))

            # validate input
            if wager < 0:
                print('Please enter a positive bet:', end='')
        except ValueError:
            print('Please enter a bet:', end='')

    # return user input
    return wager

def diceRoll():
    """
    this function rolls the dice
    """
    # assign variables with random int
    dice1 = randint(1,6)
    dice2 = randint(1,6)

    print('the LOTS CAST is', ('-' * 17), (dice1 + dice2), sep='')

    # return variables
    return dice1 + dice2

def remainingTotal(completeTotal):
    """
    this function displays the remaining total
    """
    print('Your remaining total is', completeTotal)

def zeroTalentsRemaining(talents):
    """
    this function ends the program if no talents remain
    """
    # create if statement if talents are less than zero
    if talents <= 0:
        print('You are out of talents.')

        # return true for ending flag
        return True

def fieldBetPlay(dice, wager, total):
    """
    this function plays the field bet
    """
    # create lists for possible play types
    doubleList = [2,12]
    evenList = [3,4,9,10,11]
    loseList = [5,6,7,8]

    # play dice variable in doubles list
    if dice in doubleList:
        print('You won double your bet.')

        # return double wager and add to total
        return total + (wager * 2)

    # play dice variable in evens list
    elif dice in evenList:
        print('You won your bet.')

        # return wager and add to total
        return total + wager

    # play dice in lose list
    else:
        print('You lost.')

        # return wager sutracted from total
        return total + (wager * -1)

def passBetPlay(dice, wager, total):
    """
    this function plays the pass bet
    """
    # create play lists
    evenList = [7,11]
    loseList = [2,12]
    pointList = [3,4,5,6,8,9,10]

    # play dice variable in even list
    if dice in evenList:
        print('You won your bet.')

        # return wager and add total
        return total + wager

    # play dice variable in lose list
    elif dice in loseList:
        print('You lost.')

        # return subtracted wager from total
        return total + (wager * -1)

    # play dice in point list
    elif dice in pointList:
        # assign variable to original dice value
        pointMove = dice

        # assign variable to new dice roll 
        dice = diceRoll()

        # create loop to roll again
        while dice not in evenList and dice != pointMove:
                # assign variable
                dice = diceRoll()

        # play dice if it equals original roll
        if dice == pointMove:
            print('You won your bet.')

            # return total with added wager
            return total + wager

        # play dice if it is in the list to lose
        elif dice in evenList:
            print('You lose your bet.')

            # return total with subtracted wager 
            return total + (wager * -1)

def appendNewTotal(talents):
    """
    this function adds new talents total to file
    """
    # open file
    gameFile = open('talents.txt', 'w')

    # write data onto file as string
    gameFile.write(str(talents))

    # close file
    gameFile.close()
    print('Goodbye.')
    
# call function
main()

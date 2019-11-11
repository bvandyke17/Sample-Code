# bvandyke17@georgefox.edu
# program 10
# 2017-11-15

# assign global variables for values
ROCK = 0
PAPER = 1
SCISSORS = 2

# assign global variables to all valid input
PLAY_LIST = ['Rock', 'rock', 'Paper', 'paper', 'Scissors', 'scissors', '2']

# import random int
from random import randint, choice


def main ():
    """
    this is the main function that is called
    """
    # assign flag to run/quit program
    done = userMenu() 

    # assign running variables
    userWin = 0
    cpuWin = 0
    tie = 0

    # start loop with true/false flag
    while not done:
        # get user input for their move 
        # assign flag if user decides to quit
        userPlay, done = userChoice()

        # assign variable for random computer move
        cpuPlay = cpuNumber()

        # reassign wins & ties for all rounds played
        userWin, cpuWin, tie = playMove(userPlay, cpuPlay, tie, userWin, cpuWin)

    # display total scores
    displayScore(userWin, cpuWin, tie)

    # show who wins most rounds
    calcWinner(userWin, cpuWin)

def userMenu():
    """
    this function gets user input and validates to play/quit acting as a menu
    """
    # assign variable to start loop
    choiceNum = 0

    # start loop to validate
    while choiceNum != 1 and choiceNum != 2:
        try:
            # assign variable for user input
            choiceNum = int(input('1 to play, 2 to quit:'))

        # except error
        except ValueError:
            print('Error.')

    # assign variable to continue or end loop
    if choiceNum == 1:
        done = False

    else:
        done = True

    # return variable 
    return done

# create user input function and validate
def userChoice():
    """
    this function gets the users move they want to play or quits
    """
    # assign variable to start loop
    userPlay = 0

    # create falg to return to end main() loop 
    done = False

    # get user input
    userPlay = str(input('Rock, Paper, or Scissors? (2 to quit):'))

    # create loop to validate input if not in global list of plays allowed
    while userPlay not in PLAY_LIST:
            # display error if invalid
            print('Error: Enter a valid option.')

            # get user input 
            userPlay = str(input('Rock, Paper, or Scissors?'))

    # assign user input string to rock/paper/scissors global variable
    if userPlay == PLAY_LIST[0] or userPlay == PLAY_LIST[1]:
        userPlay = ROCK

    elif userPlay == PLAY_LIST[2] or userPlay == PLAY_LIST[3]:
        userPlay = PAPER

    elif userPlay == PLAY_LIST[4] or userPlay == PLAY_LIST[5]:
        userPlay = SCISSORS

    else: 
        # assign flag to end main() 
        done = True

    # return variables
    return userPlay, done

def cpuNumber():
    """
    this function generates random number for the computer move 
    and displays what move it played
    """
    # assign variable to random number
    number = randint(0,2)

    # display what move the computer played
    if number == 0:
        print('Computer played', PLAY_LIST[0])

    elif number == 1:
        print('Computer played', PLAY_LIST[2])

    else:
        print('Computer played', PLAY_LIST[4])

    # return variable 
    return number

def playMove(player, cpu, tie, playerWins, cpuWins):
    """
    this function plays user input vs compter and adds result to running totals
    """
    # calc result for tie and dispaly
    # add result to running total
    if player == cpu:
        print('Tie.')
        tie += 1

    # calc result for possible plays and display 
    # add result to running total 
    elif player == ROCK and cpu == SCISSORS:
        print('You Win.')
        playerWins += 1

    elif player == ROCK and cpu == PAPER:
        print('Computer Wins.')
        cpuWins += 1

    elif player == PAPER and cpu == ROCK:
        print('You Win.')
        playerWins += 1

    elif player == PAPER and cpu == SCISSORS:
        print('Computer Wins.')
        cpuWins += 1

    elif player == SCISSORS and cpu == ROCK:
        print('Computer Wins.')
        cpuWins += 1

    elif player == SCISSORS and cpu == PAPER:
        print('You Win.')
        playerWins += 1

    # return wins and ties
    return playerWins, cpuWins, tie

def displayScore(playerWins, cpuWins, ties):
    """
    this function displays the scores
    """
    # display data
    print('You:', playerWins, '\tComputer:', cpuWins, '\tTies:', ties)

def calcWinner(yourScore, cpuScore):
    """
    this function calcs and displays who won
    """
    # calc total amount of rounds
    # display data
    if yourScore > cpuScore:
        print('You won the most rounds!')

    elif yourScore < cpuScore:
        print('Computer won the most rounds.')

    else:
        print('You and the computer tied rounds.')

# call main function
main()
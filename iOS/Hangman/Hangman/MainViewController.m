//
//  MainViewController.m
//  Hangman
//
//  Created by Tom LePage on 10/16/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "MainViewController.h"
#import "WordManager.h"

@interface MainViewController ()
{
    WordManager *wordManager;
    NSMutableArray *usedLetters;
    NSArray *letters;
    int numberOfGuesses;
    BOOL evil;
    BOOL gameOver;
    NSArray *normalComputerSayings;
    NSArray *evilComputerSayings;
}
@end

@implementation MainViewController

@synthesize wordField = _wordField;
@synthesize wordLabel = _wordLabel;
@synthesize statusView = _statusView;
@synthesize lettersRemainingLabel = _lettersRemainingLabel;
@synthesize computerCommentField = _computerCommentField;

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Set the text field to hidden
	self.wordField.hidden = YES;
    
    // Displays the keyboard
    [self.wordField becomeFirstResponder];

    // Set the guesses to full
    self.statusView.progress = 1.0;
    
    // Create Word Manager and generate word to use
    self->wordManager = [[WordManager alloc] init];
    
    // Set new word to label's text
    self.wordLabel.text = self->wordManager.maskedWord;
    
    // Set up used letters dictionary
    [self loadUsedLetters];
    
    // Set default values
    NSMutableDictionary *defaultValues = [[NSMutableDictionary alloc] init];
    [defaultValues setObject:@"4" forKey:@"letters"];
    [defaultValues setObject:@"NO" forKey:@"type"];
    [defaultValues setObject:@"5" forKey:@"guesses"];
    
    // Register defaults
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    [defaults registerDefaults:defaultValues];
    
    // Default number of guesses to 5 in case they don't go to settings
    self->numberOfGuesses = [defaults integerForKey:@"guesses"];
    
    // Get game type
    self->evil = [defaults boolForKey:@"type"];
    
    // Load sayings for computer
    self->normalComputerSayings = [[NSArray alloc] initWithObjects:@"I don't think so - try again"
                                   @"Close...but no",
                                   @"Way off!  Try again", nil];
    self->evilComputerSayings = [[NSArray alloc] initWithObjects:@"Are you paying attention?"
                                 @"I feel the IQ level dropping in here..." @"You've redefined 'idiot' in the dictionary", nil];
    
    // Set game over flag
    self->gameOver = NO;
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

 - (void)loadUsedLetters
{
    // Load each letter as the key, and the value for each will initially be false
    self->usedLetters = [[NSMutableArray alloc] initWithObjects:
                         [NSNumber numberWithInt:0], // A
                         [NSNumber numberWithInt:0], // B
                         [NSNumber numberWithInt:0], // C
                         [NSNumber numberWithInt:0], // D
                         [NSNumber numberWithInt:0], // E
                         [NSNumber numberWithInt:0], // F
                         [NSNumber numberWithInt:0], // G
                         [NSNumber numberWithInt:0], // H
                         [NSNumber numberWithInt:0], // I
                         [NSNumber numberWithInt:0], // J
                         [NSNumber numberWithInt:0], // K
                         [NSNumber numberWithInt:0], // L
                         [NSNumber numberWithInt:0], // M
                         [NSNumber numberWithInt:0], // N
                         [NSNumber numberWithInt:0], // O
                         [NSNumber numberWithInt:0], // P
                         [NSNumber numberWithInt:0], // Q
                         [NSNumber numberWithInt:0], // R
                         [NSNumber numberWithInt:0], // S
                         [NSNumber numberWithInt:0], // T
                         [NSNumber numberWithInt:0], // U
                         [NSNumber numberWithInt:0], // V
                         [NSNumber numberWithInt:0], // W
                         [NSNumber numberWithInt:0], // X
                         [NSNumber numberWithInt:0], // Y
                         [NSNumber numberWithInt:0], // Z
                         nil];
    
    self->letters = [[NSArray alloc] initWithObjects:
                     @"A",
                     @"B",
                     @"C",
                     @"D",
                     @"E",
                     @"F",
                     @"G",
                     @"H",
                     @"I",
                     @"J",
                     @"K",
                     @"L",
                     @"M",
                     @"N",
                     @"O",
                     @"P",
                     @"Q",
                     @"R",
                     @"S",
                     @"T",
                     @"U",
                     @"V",
                     @"W",
                     @"X",
                     @"Y",
                     @"Z",
                     nil];
}

- (BOOL)checkIfLetterUsed:(NSString *)letterTyped
{
    BOOL used = NO;
    
    int indexOfLetter = [self->letters indexOfObject:[letterTyped uppercaseString]];
    
    int usedValue = [(NSNumber *)[self->usedLetters objectAtIndex:indexOfLetter] intValue];
    
    used = (usedValue == 0 ? NO : YES);
    
    return used;
}

- (void)updateUsedLetters:(NSString *)letter
{
    NSMutableString *updatedString;
    
    // Update array    
    [self->usedLetters replaceObjectAtIndex:[self->letters indexOfObject:[letter uppercaseString]] withObject:[NSNumber numberWithInt:1]];
    
    // Update label
    updatedString = [[NSMutableString alloc] initWithCapacity:26];
    for (int i = 0; i < 26; i++)
    {
        BOOL used = [self checkIfLetterUsed:[self->letters objectAtIndex:i]];
        if (!used)
        {
            [updatedString appendString:[self->letters objectAtIndex:i]];
        }
    }
    
    self.lettersRemainingLabel.text = [updatedString uppercaseString];
    updatedString = nil;
}

#pragma mark - Flipside View

- (void)flipsideViewControllerDidFinish:(FlipsideViewController *)controller
{
    // get number of guesses value from settings
    self->numberOfGuesses = controller.guessSlider.value;
    self->evil = controller.typeSwitch.on;
    
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (IBAction)showInfo:(id)sender
{    
    FlipsideViewController *controller = [[FlipsideViewController alloc] initWithNibName:@"FlipsideViewController" bundle:nil];
    controller.delegate = self;
    controller.modalTransitionStyle = UIModalTransitionStyleFlipHorizontal;
    [self presentViewController:controller animated:YES completion:nil];
}

#pragma mark - Game Methods

- (IBAction)newGame:(id)sender
{
    self.wordLabel.text = nil;
    self.statusView.progress = 1.0;
    self.computerCommentField.text = @"Ready for this?";
    
    // For Game Type = "Normal" Hangman
    [self->wordManager pickWord];
    
    self.wordLabel.text = self->wordManager.maskedWord;
    
    // Reset "can do action" flag
}

#pragma mark - UITextField Actions

- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string
{
    // Check if the game is over first
    // First, need to check if letter has been used already
    BOOL letterUsed = [self checkIfLetterUsed:string];
    
    // If it has, block the user from using it
    if (letterUsed)
    {
        // Make the computer say something here as well to indicate that the letter has been used
        if (self->evil)
        {
            self.computerCommentField.text = @"Way to pick the same letter over again";
        }
        else
        {
            self.computerCommentField.text = @"I think you've picked that one before";
        }
        
        // return out
        return NO;
    }
    
    // If not, update display to remove letter from acceptable letters
    [self updateUsedLetters:string];
    
    BOOL letterPresentInWord = [self->wordManager unveilLetter:[string uppercaseString]];
    self.wordLabel.text = self->wordManager.maskedWord;
    
    if (letterPresentInWord)
    {
        // Make the computer say something
        
        // Check for game win condition
        if (self->wordManager.maskedCharactersLeft == 0)
        {
            // User wins game (display alert)
            
            // Set a flag to indicate that no more action can be taken
        }
    }
    else
    {
        // Make the computer say something
        
        // Update status bar
        float status = [self.statusView progress];
        status -= (1.0f / self->numberOfGuesses);
        
        [self.statusView setProgress:status];
        
        // Check for loss condition
        if (status <= 0.1f)
        {
            // User loses
            // Make Computer say something
            // Display alert
        }

    }
    
    return YES;
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
    // Need to implement this
    return YES;
}

- (BOOL)textFieldShouldBeginEditing:(UITextField *)textField
{
    // Need to implement this
    return YES;
}

- (BOOL)textFieldShouldEndEditing:(UITextField *)textField
{
    // Need to implement this
    return YES;
}

@end

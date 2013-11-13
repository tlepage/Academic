//
//  QuizViewController.m
//  Quiz
//
//  Created by Tom LePage on 9/25/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "QuizViewController.h"

@interface QuizViewController ()

@end

@implementation QuizViewController

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    
    if (self)
    {
        questions = [[NSMutableArray alloc] init];
        answers = [[NSMutableArray alloc] init];
        
        [questions addObject:@"What is 7 + 7?"];
        [answers addObject:@"14"];
        
        [questions addObject:@"What is the capital of Vermont?"];
        [answers addObject:@"Montpelier"];
        
        [questions addObject:@"From what is cognac made?"];
        [answers addObject:@"Grapes"];
    }
    
    return self;
}

- (IBAction)showQuestion:(id)sender
{
    // Step to the next question
    currentQuestionIndex++;
    
    // Am I past the last question
    if (currentQuestionIndex == [questions count])
    {
        // Go back to the first question
        currentQuestionIndex = 0;
    }
    
    // Get the string at the index in the questions array
    NSString *question = [questions objectAtIndex:currentQuestionIndex];
    
    // Log the question to the console
    NSLog(@"Displaying question: %@", question);
    
    // Display the string in the question field
    [questionField setText:question];
    
    // Clear the answer field
    [answerField setText:@"???"];
}

- (IBAction)showAnswer:(id)sender
{
    // What is the answer to the current question
    NSString *answer = [answers objectAtIndex:currentQuestionIndex];
    
    // Display it
    [answerField setText:answer];
}

@end

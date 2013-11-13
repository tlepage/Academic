//
//  WordManager.m
//  Hangman
//
//  Created by Tom LePage on 10/18/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "WordManager.h"

@implementation WordManager
{
    int wordCount;
    NSArray *keys;
}

@synthesize words = _words;
@synthesize currentWord = _currentWord;
@synthesize maskedWord = _maskedWord;
@synthesize maskedCharactersLeft = _maskedCharactersLeft;

- (id)init
{
    if (self = [super init])
    {
        NSBundle *bundle = [NSBundle mainBundle];
        NSString *plistPath = [bundle pathForResource:@"smallWords" ofType:@"plist"];
        
        self.words = [[NSDictionary alloc] initWithContentsOfFile:plistPath];
        
        self->wordCount = [self.words count];
        self->keys = [self.words allKeys];
        
        for (NSString *string in self.words)
        {
            NSLog(@"words: %@", string);
        }
        
        // Pick current word
        [self pickWord];
        
        // Set amount of masked characters
        self.maskedCharactersLeft = [self.maskedWord length];
    }
    
    return self;
}

- (void)pickWord
{
    int randomNumber = arc4random() % ([self.words count] - 1);
    NSLog(@"Index: %d", randomNumber);
    
    self.currentWord = [self->keys objectAtIndex:randomNumber];
    
    int length = [self.currentWord length];
    self.maskedWord = [[NSMutableString alloc] initWithCapacity:length];
    
    // Create masked word
    for (int i = 0; i < length; i++)
    {
        [self.maskedWord appendString:@"-"];
    }
    
    NSLog(@"Word: %@", self.currentWord);
    NSLog(@"Masked Word: %@", self.maskedWord);
}

- (BOOL)unveilLetter:(NSString *)letter
{
    BOOL couldUnveil = NO;
    
    NSString *curMask = [[NSMutableString alloc] initWithCapacity:[self.maskedWord length]];
    
    // Check to see if character typed actually matches a letter in the chosen string
    int length = [self.currentWord length];
    unichar l = [letter characterAtIndex:0];
    
    for (int i = 0; i < length; i++)
    {
        unichar c = [self.currentWord characterAtIndex:i];
        
        if (l == c)
        {
            curMask = [curMask stringByAppendingFormat:@"%C", c];
            NSLog(@"Found %@: mask = %@", letter, curMask);
            couldUnveil = YES;
            self.maskedCharactersLeft--;
        }
        else
        {
            curMask = [curMask stringByAppendingFormat:@"%C", [self.maskedWord characterAtIndex:i]];
            NSLog(@"Not Found: mask = %@", curMask);
        }
    }
    
    self.maskedWord = [NSMutableString stringWithString:curMask];
    
    return couldUnveil;
}

@end

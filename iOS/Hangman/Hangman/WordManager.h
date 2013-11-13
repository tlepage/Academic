//
//  WordManager.h
//  Hangman
//
//  Created by Tom LePage on 10/18/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface WordManager : NSObject

@property (nonatomic, strong) NSDictionary *words;
@property (nonatomic, strong) NSString *currentWord;
@property (nonatomic, strong) NSMutableString *maskedWord;
@property (nonatomic, assign) int maskedCharactersLeft;

- (BOOL)unveilLetter:(NSString *)letter;
- (void)pickWord;

@end

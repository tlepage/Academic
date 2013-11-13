//
//  BNRExecutor.h
//  Blocky
//
//  Created by Tom LePage on 10/8/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface BNRExecutor : NSObject
{
    //int (^equation)(int, int);
}

@property (nonatomic, copy) int (^equation)(int, int); // copy allows for a strong reference

// When a function takes a block as input, you just move the name of the block to after, i.e.
// this would look like int (^block)(int, int);
- (void)setEquation:(int (^)(int, int))block;
- (int)computeWithValue:(int)value1 andValue:(int)value2;

@end

//
//  BNRExecutor.m
//  Blocky
//
//  Created by Tom LePage on 10/8/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "BNRExecutor.h"

@implementation BNRExecutor
@synthesize equation = _equation;

/*
- (void)setEquation:(int (^)(int, int))block
{
    _equation = block;
}
*/

- (int)computeWithValue:(int)value1 andValue:(int)value2
{
    // If a block variable is executed but doesn't point at a block,
    // it will crash - return 0 if there is no block
    if (!_equation)
    {
        return 0;
    }
    
    // Return value of block with value1 and value2
    return _equation(value1, value2);
}

- (void)dealloc
{
    NSLog(@"Executor is being destroyed...");
}

@end

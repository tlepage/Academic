//
//  CalculatorBrain.m
//  Calculator
//
//  Created by Tom LePage on 7/12/12.
//  Copyright (c) 2012 Cash America Intl. All rights reserved.
//

#import "CalculatorBrain.h"

@interface CalculatorBrain()
@property (nonatomic, strong) NSMutableArray *programStack;
@end

@implementation CalculatorBrain

@synthesize programStack = _programStack;

- (NSMutableArray *) programStack
{
    if (_programStack == nil) 
        _programStack = [[NSMutableArray alloc] init];
    
    return _programStack;
}

- (void) pushOperand:(double)operand
{
    [self.programStack addObject:[NSNumber numberWithDouble:operand]];
}

- (double) performOperation:(NSString *)operation
{
    [self.programStack addObject:operation];
    return [CalculatorBrain runProgram:self.program];
    // if used [self class] here, this could possible use inheritance
    // and an overridden version of the method
}

- (id) program
{
    return [self.program copy]; // returns an immutable array
}

+ (NSString *) descriptionOfTheProgram:(id)program
{
    return @"Implement this in assignment 2";
}

+ (double) popOperandOffStack:(NSMutableArray *) stack
{
    double result = 0;
    
    id topOfStack = [stack lastObject];
    if (topOfStack)
    {
        [stack removeLastObject];
    }
    
    if ([topOfStack isKindOfClass:[NSNumber class]])
    {
       return [topOfStack doubleValue];
    }
    else if ([topOfStack isKindOfClass:[NSString class]])
    {
        NSString *operation = topOfStack;
        
        // calculate result
        // recursively call popOperandOffStack
        if ([operation isEqualToString:@"+"])
        {
            result = [self popOperandOffStack:stack] + [self popOperandOffStack:stack];
        }
        else if ([@"*" isEqualToString:operation])
        {
            result = [self popOperandOffStack:stack] * [self popOperandOffStack:stack];
        }
        else if ([@"-" isEqualToString:operation])
        {
            double subtrahend = [self popOperandOffStack:stack];
            result = [self popOperandOffStack:stack] - subtrahend;
        }
        else if ([@"/" isEqualToString:operation])
        {
            double divisor = [self popOperandOffStack:stack];
            if (divisor)
                result = [self popOperandOffStack:stack] / divisor;
        }
    }
    
    return result;
}

+ (double) runProgram:(id)program
{
    NSMutableArray *stack;
    if ([program isKindOfClass:[NSArray class]])
    {
        stack = [program mutableCopy];
    }
    return [self popOperandOffStack:stack];
}


@end

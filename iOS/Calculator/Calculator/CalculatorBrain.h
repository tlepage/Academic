//
//  CalculatorBrain.h
//  Calculator
//
//  Created by Tom LePage on 7/12/12.
//  Copyright (c) 2012 Cash America Intl. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CalculatorBrain : NSObject

- (void) pushOperand:(double)operand;
- (double) performOperation:(NSString *)operation;

@property (readonly) id program;

+ (double) runProgram:(id)program;
+ (NSString *) descriptionOfTheProgram:(id)program;

@end

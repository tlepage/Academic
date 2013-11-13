//
//  SimpleTableViewController.m
//  SimpleTable
//
//  Created by Tom LePage on 8/23/12.
//  Copyright (c) 2012 Tom LePage. All rights reserved.
//

#import "SimpleTableViewController.h"
#import "SimpleTableCell.h"

@interface SimpleTableViewController ()

@end

@implementation SimpleTableViewController

@synthesize tableData = _tableData;
@synthesize thumbnails = _thumbnails;
@synthesize prepTimes = _prepTimes;

- (void)viewDidLoad
{
    [super viewDidLoad];
    
	// Load the data array
    _tableData = [NSArray arrayWithObjects:
                  @"Egg Benedict",
                  @"Mushroom Risotto",
                  @"Full Breakfast",
                  @"Hamburger",
                  @"Ham and Egg Sandwich",
                  @"Creme Brulee",
                  @"White Chocolate Donut",
                  @"Starbucks Coffee",
                  @"Vegetable Curry",
                  @"Instant Noodle with Egg",
                  @"Noodle with BBQ Pork",
                  @"Japanese Noodle with Pork",
                  @"Green Tea",
                  @"Thai Shrimp Cake",
                  @"Angry Birds Cake",
                  @"Ham and Cheese Panini",
                  nil];
    
    // Load the thumbnail array
    _thumbnails = [NSArray arrayWithObjects:
                   @"egg_benedict.jpg",
                   @"mushroom_risotto.jpg",
                   @"full_breakfast.jpg",
                   @"hamburger.jpg",
                   @"ham_and_egg_sandwich.jpg",
                   @"creme_brulee.jpg",
                   @"white_chocolate_donut.jpg",
                   @"starbucks_coffee.jpg",
                   @"vegetable_curry.jpg",
                   @"instant_noodle_with_egg.jpg",
                   @"noodle_with_bbq_pork.jpg",
                   @"japanese_noodle_with_pork.jpg",
                   @"green_tea.jpg",
                   @"thai_shrimp_cake.jpg",
                   @"angry_birds_cake.jpg",
                   @"ham_and_cheese_panini.jpg",
                   nil];
    
    // Load the prep time array
    _prepTimes = [NSArray arrayWithObjects:
                  @"30 minutes",
                  @"30 minutes",
                  @"45 minutes",
                  @"30 minutes",
                  @"15 minutes",
                  @"10 minutes",
                  @"1 hour",
                  @"45 minutes",
                  @"15 minutes",
                  @"1 hour",
                  @"20 minutes",
                  @"45 minutes",
                  @"5 minutes",
                  @"1 hour",
                  @"2 hours",
                  @"15 minutes",
                  nil];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    return (interfaceOrientation != UIInterfaceOrientationPortraitUpsideDown);
}

// inform the tableView how many rows we are working with
- (NSInteger) tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
    return [_tableData count];
}

// called every time a row is displayed
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *simpleTableIdentifier = @"SimpleTableItem";
    
    // define what the cells are going to be like
    //UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
    SimpleTableCell *cell = (SimpleTableCell *)[tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
    
    if (cell == nil)
    {
        //cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:simpleTableIdentifier];
        NSArray *views = [[NSBundle mainBundle] loadNibNamed:@"SimpleTableCell" owner:self options:nil];
        for (UIView *view in views)
        {
            if ([view isKindOfClass:[UITableViewCell class]])
            {
                cell = (SimpleTableCell *)view;
            }
        }
    }
    
    // add text
    //cell.textLabel.text = [_tableData objectAtIndex:indexPath.row];
    
    // add image
    //cell.imageView.image = [UIImage imageNamed:[_thumbnails objectAtIndex:indexPath.row]];
    cell.nameLabel.text = [_tableData objectAtIndex:indexPath.row];
    cell.thumbnailImageView.image = [UIImage imageNamed:[_thumbnails objectAtIndex:indexPath.row]];
    cell.timeLabel.text = [_prepTimes objectAtIndex:indexPath.row];
    
    return cell;
}

- (CGFloat)tableView:(UITableView *)tableView heightForRowAtIndexPath:(NSIndexPath *)indexPath
{
    return 78;
}

@end

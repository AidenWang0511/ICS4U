|| maze[i+1][j] == exitChar || maze[i-1][j] == exitChar){ //not part of algorithm
                    maze[i][j] = openChar; //if the cell is next to the
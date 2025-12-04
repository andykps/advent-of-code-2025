my ($filename) = @ARGV;
my $ROLL = '@';
open my $handle, '<', $filename or die "Could not open file: $!";
chomp(my @lines = <$handle>);
close $handle;

my $accessible = 0;

for my $y (0 .. $#lines) {
    my @line = split //, $lines[$y];
    for my $x (0 .. $#line) {
        if (@line[$x] eq $ROLL) {
            my $neighbours = 0;
            for my $dy (-1 .. 1) {
                for my $dx (-1 .. 1) {
                    next if $dx == 0 && $dy == 0;
                    my $nx = $x + $dx;
                    my $ny = $y + $dy;
                    if ($nx >= 0 && $nx <= $#line && $ny >= 0 && $ny <= $#lines) {
                        if (substr($lines[$ny], $nx, 1) eq $ROLL) {
                            $neighbours++;
                        }
                    }
                }
            }
            if ($neighbours < 4) {
                $accessible++;
            }
#            print "$x,$y has $neighbours neighbours\n";
        }
    }
}
print "$accessible\n";

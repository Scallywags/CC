program gauss;

var total : integer;
var number : integer;
var max : integer;

begin
    in ("max", max);
    while number LE max do
        begin
            total := total + number;
            number := number  + 1
        end
    ;
    out total
end;
.

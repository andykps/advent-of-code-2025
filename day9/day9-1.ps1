param([string] $filePath="input.txt")
$tiles = @()
$areas = @()
Import-Csv -Path $filePath -Header 'x', 'y' | ForEach-Object {
    $tiles += $_
}
for ($i = 0; $i -lt $tiles.Count; $i++) {
    for ($j = $i + 1; $j -lt $tiles.Count; $j++) {
        $areas += @{
            p1 = $tiles[$i]
            p2 = $tiles[$j]
            area = (1 + [math]::Abs($tiles[$i].x - $tiles[$j].x)) * (1 + [math]::Abs($tiles[$i].y - $tiles[$j].y))
        }
    }
}
Write-Host ($areas | Sort-Object area -Descending | Select-Object -First 1).area

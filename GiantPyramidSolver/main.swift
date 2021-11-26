//  Copyright © 2019 Maksim Khrapov. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import Foundation

typealias Position = UInt64
var Final: Position = 0
var AllPossiblePositions: [[Position]] = Array(repeating: [], count: 9)
var choices: [Int] = Array(repeating: 0, count: 9)

func pos3(_ i: Int, _ j: Int, _ k: Int) -> Position {
    var p: Position = 0
    p = p | (1 << i)
    p = p | (1 << j)
    p = p | (1 << k)
    return p
}

func pos4(_ i: Int, _ j: Int, _ k: Int, _ l: Int) -> Position {
    var p: Position = 0
    p = p | (1 << i)
    p = p | (1 << j)
    p = p | (1 << k)
    p = p | (1 << l)
    return p
}


let planes = [
    // planes parallel to the sides
    [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14],
    [15, 16, 17, 18, 19, 20, 21, 22, 23, 24],
    [25, 26, 27, 28, 29, 30],
    
    [0, 5, 9, 12, 14, 15, 19, 22, 24, 25, 28, 30, 31, 33, 34],
    [1, 6, 10, 13, 16, 20, 23, 26, 29, 32],
    [2, 7, 11, 17, 21, 27],
    
    [0, 1, 2, 3, 4, 15, 16, 17, 18, 25, 26, 27, 31, 32, 34],
    [5, 6, 7, 8, 19, 20, 21, 28, 29, 33],
    [9, 10, 11, 22, 23, 30],
    
    [4, 8, 11, 13, 14, 18, 21, 23, 24, 27, 29, 30, 32, 33, 34],
    [3, 7, 10, 12, 17, 20, 22, 26, 28, 31],
    [2, 6, 9, 16, 19, 25],
    
    // planes parallel to the edges
    [5, 6, 7, 8, 15, 16, 17, 18],
    [9, 10, 11, 19, 20, 21, 25, 26, 27],
    [12, 13, 22, 23, 28, 29, 31, 32],
    
    [1, 6, 10, 13, 15, 19, 22, 24],
    [2, 7, 11, 16, 20, 23, 25, 28, 30],
    [3, 8, 17, 21, 26, 29, 31, 33],
    
    [3, 7, 10, 12, 18, 21, 23, 24],
    [2, 6, 9, 17, 20, 22, 27, 29, 30],
    [1, 5, 16, 19, 26, 28, 32, 33],
]


let coords = [
    (0.0, 0.0, 0.0),
    (1.0, 0.0, 0.0),
    (2.0, 0.0, 0.0),
    (3.0, 0.0, 0.0),
    (4.0, 0.0, 0.0),
    (0.5, 0.866, 0.0),
    (1.5, 0.866, 0.0),
    (2.5, 0.866, 0.0),
    (3.5, 0.866, 0.0),
    (1.0, 1.732, 0.0),
    (2.0, 1.732, 0.0),
    (3.0, 1.732, 0.0),
    (1.5, 2.598, 0.0),
    (2.5, 2.598, 0.0),
    (2.0, 3.464, 0.0),
    (0.5, 0.28867, 0.8165),
    (1.5, 0.28867, 0.8165),
    (2.5, 0.28867, 0.8165),
    (3.5, 0.28867, 0.8165),
    (1.0, 1.15467, 0.8165),
    (2.0, 1.15467, 0.8165),
    (3.0, 1.15467, 0.8165),
    (1.5, 2.02067, 0.8165),
    (2.5, 2.02067, 0.8165),
    (2.0, 2.88667, 0.8165),
    (1.0, 0.57734, 1.633),
    (2.0, 0.57734, 1.633),
    (3.0, 0.57734, 1.633),
    (1.5, 1.44334, 1.633),
    (2.5, 1.44334, 1.633),
    (2.0, 2.30934, 1.633),
    (1.5, 0.86601, 2.4495),
    (2.5, 0.86601, 2.4495),
    (2.0, 1.73201, 2.4495),
    (2.0, 1.15468, 3.266)
]
    
let knownDistances = [
    [1.0, 1.0, 1.0, 1.732, 2.0, 2.64575],
    [1.0, 1.0, 1.0, 1.0, 1.732, 2.0],
    [1.0, 1.0, 1.0, 1.732, 1.732, 2.64575],
    [1.0, 1.0, 1.0, 1.732, 1.732, 2.0],
    [1.0, 1.0, 1.0, 1.41421, 2.0, 2.23606]
]
    
    
func aboutEqual(_ a: Double, _ b: Double) -> Bool {
    var c = a - b
    if c < 0 {
        c = c * -1.0
    }
    
    return c < 0.01
}
    
    
func distance(_ i: Int, _ j: Int) -> Double {
    var a = i
    var b = j
    if(i > j) {
        (a, b) = (b, a)
    }
    
    let (x1, y1, z1) = coords[a]
    let (x2, y2, z2) = coords[b]
    
    let dx = x1 - x2
    let dy = y1 - y2
    let dz = z1 - z2
    let sq = dx*dx + dy*dy + dz*dz
    
    return sq.squareRoot()
}


func isMatch(_ a: [Double], _ b: [Double]) -> Bool {
    for i in 0...5 {
        if !aboutEqual(a[i], b[i]) {
            return false
        }
    }
    return true
}


func match(_ i: Int, _ j: Int, _ k: Int, _ l: Int) {
    var distances: [Double] = []
    distances.append(distance(i, j))
    distances.append(distance(i, k))
    distances.append(distance(i, l))
    distances.append(distance(j, k))
    distances.append(distance(j, l))
    distances.append(distance(k, l))
    
    distances.sort()
    
    if(distances[5] > 2.66) {
        return
    }
    
    for (index, knownDistance) in knownDistances.enumerated() {
        if isMatch(distances, knownDistance) {
            if index == 4 {
                AllPossiblePositions[index+1].append(pos4(i, j, k, l))
                AllPossiblePositions[index+2].append(pos4(i, j, k, l))
                AllPossiblePositions[index+3].append(pos4(i, j, k, l))
                AllPossiblePositions[index+4].append(pos4(i, j, k, l))
            }
            else {
                AllPossiblePositions[index+1].append(pos4(i, j, k, l))
            }
            return
        }
    }
}


func isPlanar(_ i: Int, _ j: Int, _ k: Int, _ l: Int) -> Bool {
    for plane in planes {
        if plane.contains(i) && plane.contains(j) && plane.contains(k) && plane.contains(l) {
            return true
        }
    }
    return false
}

// positions for other pieces have to be calculated
func precompute() {
    for i in 0...31 {
        for j in (i+1)...32 {
            for k in (j+1)...33 {
                for l in (k+1)...34 {
                    if isPlanar(i, j, k, l) {
                        match(i, j, k, l)
                    }
                }
            }
        }
    }
}


func initialize() {
    // positions for the 3-ball piece have been
    // determined by visual examination and account for
    // duplicates
    AllPossiblePositions[0].append(pos3(0, 1, 2))
    AllPossiblePositions[0].append(pos3(1, 2, 3))
    AllPossiblePositions[0].append(pos3(5, 6, 7))
    AllPossiblePositions[0].append(pos3(9, 10, 11))
    AllPossiblePositions[0].append(pos3(19, 20, 21))
    
    // establish the end
    for i in 0..<35 {
        Final = Final | (1 << i)
    }
}


func search(_ level: Int, _ prev: Position) -> Bool {
    if level == 9 {
        if prev == Final {
            return true
        }
        return false
    }

    for (index, pos) in AllPossiblePositions[level].enumerated() {
        if (prev&pos) == 0 {
            if search(level+1, prev|pos) {
                choices[level] = index
                return true
            }
        }
    }

    return false
}


func displaySolution() {
    var occupied: [Int] = Array(repeating: 0, count: 35)

    for i in 0..<9 {
        let p = AllPossiblePositions[i][choices[i]]
        for j in 0..<35 {
            if p&(1<<j) != 0 {
                occupied[j] = i
            }
        }
    }

    for i in 0..<35 {
        print(occupied[i]+1, terminator: " ")
    }
    print()
}



func main() {
    print("Start precompute: ", Date())
    initialize()
    precompute()
    let expected = [5, 384, 336, 96, 168, 96, 96, 96, 96, 96]
    for i in 0..<9 {
        if AllPossiblePositions[i].count != expected[i] {
            print("i: \(i) Expected: \(expected[i]) Got: \(AllPossiblePositions[i].count)")
            exit(1)
        }
    }
    
    //let solution = [0, 216, 81, 24, 157, 26, 33, 65, 89]
     
    print("Start search: ", Date())
    let start = DispatchTime.now()
    let res = search(0, 0)
    let end = DispatchTime.now()
    print("Finished Search: ", Date())
    
    let nanoTime = end.uptimeNanoseconds - start.uptimeNanoseconds
    let timeInterval = Double(nanoTime) / 1_000_000_000
    print("Time elapsed: \(timeInterval)")
    
    if res {
        displaySolution()
    }
    else {
        print("Solution not found")
    }
}


main()

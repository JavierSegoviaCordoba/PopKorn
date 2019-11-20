package cc.popkorn.example.model

import cc.popkorn.annotations.Alias
import cc.popkorn.annotations.ForEnvironments
import cc.popkorn.annotations.Injectable
import cc.popkorn.annotations.WithEnvironment

@Injectable class R1(d1:D1, d2:D2, d3:D3, d4:D4) : R1i

@Injectable class R2(d1:D5, d2:D6, d3:D7, d4:D8, d5:D9) : R2i

@Injectable class R3(@WithEnvironment("env1") d1:DiA, @WithEnvironment("envX") d2:DiA, d3:DiA) : R3i

@Injectable class R4(@WithEnvironment("env1") d1:DiB, @WithEnvironment("env2") d2:DiB, d3:DiB) : R4i

@Injectable class R5(@WithEnvironment("env1") d1:DiC, @WithEnvironment("env2") d2:DiC, @WithEnvironment("env3") d3:DiC, d4:DiC) : R5i

@Injectable class R6(@WithEnvironment("env4") d1:DiC, @WithEnvironment("env4") d2:DiC, @Alias("is7") d3:DiC, @Alias("is8") d4:DiC) : R6i

@Injectable class R7(d1:DiD, @WithEnvironment("envX") d2:DiD, d3:D10) : R7i

@Injectable
@ForEnvironments("env1", "env2", "env3", "env4")
class R8 private constructor() : R8i{

    @ForEnvironments("env3")
    constructor(@Alias("is1") d1:DiG, @Alias("is2") d2:DiG): this()

    @ForEnvironments("env1", "env4")
    constructor(d1:DiA, @WithEnvironment("env2") d2:DiB, d3:DiC, d4:DiD): this()

    constructor(d1:D3, d2:D5, d3:D7, d4:D10): this()

}

@Injectable class R9 : R8i

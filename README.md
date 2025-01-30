# TML - TEMPO Model DSL - Embedded Systems Runtime Verification
![TEMPO_MDSL](https://img.shields.io/badge/TEMPO_MDSL-0.1.0-gray?labelColor=purple&style=flat)

## Overview
The increasing use of embedded systems in multi-purpose applications, such as mobile devices, vehicles, and IoT/cyber-physical systems, demands greater flexibility in processing various applications and communication protocols. This flexibility is typically achieved through general-purpose processors, which configure and control multiple peripherals. However, this also increases the reliance on **hardware-dependent software (HdS)**, which is critical and highly error-prone due to its low-level interaction with hardware components.

## Problem Statement
HdS development is challenging due to its close interaction with hardware, making it susceptible to access violations and misinterpretations of communication protocols. These issues arise from informal specifications commonly found in **datasheets**, which lack explicit mechanisms to ensure the correctness of device-driver interactions. To address this, a more structured approach is necessary to **formalize and validate temporal properties** in hardware-software communication.

## Proposed Solution
This project introduces a methodology for the **formalization and runtime validation** of temporal properties in high-level communication protocols between devices and drivers. The approach leverages **contract-based monitoring** to detect access violations by enforcing protocol specifications during:
- **Virtual platform simulations**
- **Execution on real hardware platforms**

### Key Features:
- **Domain-Specific Language (DSL)**: Designed to define expected behaviors based on protocol specifications, enhancing platform-based designs.
- **Iterative Refinement**: Allows incremental improvement of communication protocol specifications alongside platform development.
- **Efficient Monitoring**: Detects critical HdS bugs with minimal design overhead and negligible runtime performance impact.

## Experimental Results
The approach was validated through experiments using:
- **DM9000A Ethernet Controller**
- **Altera UART**

![TEMPOTool](/docs/imgs/tdevcgen.png)
*Screenshot of the TEMPOTool compiling the .tdsl description for the **DM9000A Ethernet Controller** experiment*

Results demonstrate the effectiveness of the methodology in identifying critical HdS bugs while incurring minimal design time overhead and negligible execution-time impact for real hardware platforms.

## Repository Structure
```
├── c-lib/                 # C libraries for project support
├── devc_samples/          # Examples of DSL usage
├── docs/                  # Project documentation
├── imgs/                  # Images used in the documentation
├── lib/                   # Auxiliary libraries
├── mddc_source_code/      # MDDC source code
├── modelChecking/         # Files related to model verification
├── src/                   # Main DSL source code
│   └── doublem/
│       └── tempo/
├── templates/             # Templates for code generation
├── .classpath             # Classpath configuration file
├── .project               # Project configuration file
├── README.md              # Project overview and usage instructions
└── tdevcgen.iml           # IntelliJ IDEA configuration file
```

## Getting Started
To set up and use this project, follow the instructions in the [Installation Guide](docs/INSTALL.md).

## Contributions
We welcome contributions! Please check the [CONTRIBUTING.md](docs/CONTRIBUTING.md) for guidelines.

## License
This project is licensed under the [MIT License](LICENSE).

---
**Contact:** For more details, feel free to open an issue or reach out to the maintainers.
Authors
| nome            | e-mail           |
|-----------------|------------------|
| Rafael Macieira | rmm2@cin.ufpe.br |
| Edna Barros     | ensb@cin.ufpe.br |

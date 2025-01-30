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

Results demonstrate the effectiveness of the methodology in identifying critical HdS bugs while incurring minimal design time overhead and negligible execution-time impact for real hardware platforms.

## Repository Structure
```
├── c-lib/                 # Bibliotecas C para suporte ao projeto
├── devc_samples/          # Exemplos de uso da DSL
├── docs/                  # Documentação do projeto
├── imgs/                  # Imagens utilizadas na documentação
├── lib/                   # Bibliotecas auxiliares
├── mddc_source_code/      # Código-fonte do MDDC
├── modelChecking/         # Arquivos relacionados à verificação de modelos
├── src/                   # Código-fonte principal da DSL
│   └── doublem/
│       └── tempo/
├── templates/             # Modelos para geração de código
├── .classpath             # Arquivo de configuração do classpath
├── .project               # Arquivo de configuração do projeto
├── README.md              # Visão geral e instruções de uso do projeto
└── tdevcgen.iml           # Arquivo de configuração do IntelliJ IDEA
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

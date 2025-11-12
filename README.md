# Battleship

Basic academic version of Battleship game to build upon.

Contributors:
- António Moura - 122622
- Gonçalo Batista - 122623
- Afonso Lopes - 122631
- Rafael Silva - 122638

Code Smells encontrados através das funções de Teste:
- Dead Code encontrado na classe ShipTest.java, na função getTopMostPos(), já que alteração de posição não servia para nada, já que a posição dada é sempre uma das mais acima. 
- Dead Code encontrado na classe ShipTest.java, na função getLeftMostPos(), já que alteração de posição não servia para nada, já que a posição dada é a mais à esquerda, excepto na construção dos galeões. 
- Dead Code encontrado na classe Caravel.java, pois a verificação do "bearing" já é feita na superclasse Ship.java. Assim, o código foi comentado para resolver o problema.
- Dead code encontrado na classe Galleon.java, pois a verificação do Bearing já é feita na superclasse Ship.java. Assim, o código foi comentado para resolver o problema.

## Continuous Integration

O projeto está configurado com um workflow de Integração Contínua (CI) usando GitHub Actions. O workflow está definido no ficheiro `.github/workflows/maven.yml`.

Este workflow é acionado sempre que há um `push` ou `pull request` para o branch `main`.

Os passos executados são os seguintes:
1.  **Checkout:** O código do repositório é descarregado.
2.  **Set up JDK 17:** O ambiente é configurado com Java Development Kit (JDK) 17.
3.  **Build with Maven:** O projeto é compilado e empacotado usando o Maven (`mvn -B package`).
4.  **Run tests:** Os testes unitários são executados para garantir a qualidade e o correto funcionamento do código (`mvn -B -ntp -DskipTests=false verify`).
5.  **Update dependency graph:** O gráfico de dependências do projeto é atualizado para o Dependabot.


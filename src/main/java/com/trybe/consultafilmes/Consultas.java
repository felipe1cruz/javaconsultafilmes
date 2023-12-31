package com.trybe.consultafilmes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Classe para realizar consultas na classe filmes.
 */
public class Consultas {

  private final Collection<Filme> filmes;

  public Consultas(Collection<Filme> filmes) {
    this.filmes = filmes;
  }

  /**
   * Consulta 1: a partir da coleção de filmes desta classe, este método retorna o conjunto de
   * atores que interpretaram a si próprios em pelo menos um desses filmes.
   *
   * <p>
   * Considera-se "atores que interpretaram a si próprios" aqueles que têm o seu nome como uma das
   * chaves do Map `atoresPorPersonagem` e também como um dos itens pertencentes ao conjunto
   * associado a esta mesma chave.
   * </p>
   */
  public Set<String> atoresQueInterpretaramSiProprios() {
    Set<String> listaAtoresQueInterpretaramSiProprios = new HashSet<String>();

    for (Filme filme : filmes) {
      Map<String, Set<String>> atoresPorPersonagem = filme.atoresPorPersonagem;
      for (Map.Entry<String, Set<String>> entrada : atoresPorPersonagem.entrySet()) {
        String personagem = entrada.getKey();
        Set<String> atores = entrada.getValue();
        Set<String> atorQueInterpretouSiMesmo =
            atores.stream().filter(ator -> ator.equals(personagem)).collect(Collectors.toSet());

        listaAtoresQueInterpretaramSiProprios.addAll(atorQueInterpretouSiMesmo);

      }
    }
    return listaAtoresQueInterpretaramSiProprios;
  }

  /**
   * Consulta 2: a partir da coleção de filmes desta classe, este método retorna a lista de atores
   * que atuaram em pelo menos um filme de um determinado diretor. A lista retornada está disposta
   * em ordem alfabética.
   *
   * <p>
   * Considera-se que um ator tenha atuado em um filme de um determinado diretor se ele tem o seu
   * nome como um dos itens do campo `atores`, ao mesmo tempo em que o diretor em questão tem o seu
   * nome como um dos itens do campo `diretores` do mesmo filme.
   * </p>
   */
  public List<String> atoresQueAtuaramEmFilmesDoDiretorEmOrdemAlfabetica(String diretor) {
    List<String> atoresQueAturamComDiretor = new ArrayList<>();

    for (Filme filme : filmes) {
      Set<String> diretores = filme.diretores;
      Set<String> atores = filme.atores;
      for (String diretorFilme : diretores) {
        if (diretor == diretorFilme) {
          atoresQueAturamComDiretor.addAll(atores);
        }
      }
    }
    List<String> listaAtoresFiltrada =
        atoresQueAturamComDiretor.stream().distinct().sorted().collect(Collectors.toList());

    return listaAtoresFiltrada;
  }

  /**
   * Consulta 3: a partir da coleção de filmes desta classe, este método retorna a lista de filmes
   * em que pelo menos um dos diretores tenha atuado. A lista retornada está disposta em ordem de
   * lançamento, com os filmes mais recentes no início.
   *
   * <p>
   * Considera-se "filmes em que pelo menos um dos diretores tenha atuado" aqueles em que pelo menos
   * um dos itens do campo `diretores` também é um item do campo `atores`.
   * </p>
   */
  public List<Filme> filmesEmQuePeloMenosUmDiretorAtuouMaisRecentesPrimeiro() {
    Set<Filme> filmesQueDiretoresQueAtuaramProprioFilme = new HashSet<>();
    for (Filme filme : filmes) {
      Set<String> diretores = filme.diretores;
      Set<String> atores = filme.atores;
      for (String diretor : diretores) {
        if (atores.contains(diretor)) {
          filmesQueDiretoresQueAtuaramProprioFilme.add(filme);
        }
      }
    }
    List<Filme> filmesOrdenados = filmesQueDiretoresQueAtuaramProprioFilme.stream()
        .sorted((filme1, filme2) -> Integer.compare(filme2.anoDeLancamento, filme1.anoDeLancamento))
        .collect(Collectors.toList());

    return filmesOrdenados;
  }

  /**
   * Consulta 4: a partir da coleção de filmes desta classe, este método retorna um Map contendo
   * todos os filmes lançados em um determinado ano agrupados por categoria.
   *
   * <p>
   * Cada chave do Map representa uma categoria, enquanto cada valor representa o conjunto de filmes
   * que se encaixam na categoria da chave correspondente.
   * </p>
   */
  public Map<String, Set<Filme>> filmesLancadosNoAnoAgrupadosPorCategoria(int ano) {
    Map<String, Set<Filme>> filmesPorCategoria = new HashMap<>();
    for (Filme filme : filmes) {
      if (filme.anoDeLancamento == ano) {
        for (String categoria : filme.categorias) {
          filmesPorCategoria.putIfAbsent(categoria, new HashSet<>());

          filmesPorCategoria.get(categoria).add(filme);
        }
      }
    }
    return filmesPorCategoria;
  }

}

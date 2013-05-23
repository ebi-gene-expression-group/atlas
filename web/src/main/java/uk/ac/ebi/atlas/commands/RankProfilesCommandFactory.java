package uk.ac.ebi.atlas.commands;

public interface RankProfilesCommandFactory {

    public abstract RankRnaSeqProfilesCommand getRankRnaSeqProfilesCommand();

    public abstract RankMicroarrayProfilesCommand getRankMicroarrayProfilesCommand();

}
